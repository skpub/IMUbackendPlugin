package org.sk_dev.iMUbackendPlugin;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.protobuf.ByteString;
import idtoken.IDTokenProvider;
import idtoken.IdTokenProviderGrpc;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;
import java.util.UUID;

public class IdTokenProviderImpl extends IdTokenProviderGrpc.IdTokenProviderImplBase {
    private final UserCodeSet userCodeSet = UserCodeSet.getInstance();
    private final JavaPlugin plugin;

    public IdTokenProviderImpl(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void getToken(IDTokenProvider.UserCode userCode,
                         io.grpc.stub.StreamObserver<idtoken.IDTokenProvider.MaybeId> responseObserver) {

        IDTokenProvider.MaybeId response;

        if (!userCodeSet.contains(userCode)) {
            response = IDTokenProvider.MaybeId.newBuilder()
                    .setErr("Invalid OTP.")
                    .build();
        } else {
            String secret = System.getenv("ID_TOKEN_PROVIDER_SECRET");
            String issuer = System.getenv("IMU_ID_ISSUER");
            String audience = System.getenv("IMU_WEBAPP");

            String userId = userCode.getUuid();
            Instant now = Instant.now();
            Instant exp = now.plusSeconds(300);

            String jti = UUID.randomUUID().toString();

            Algorithm jwtAlg = Algorithm.HMAC256(secret);
            String idTokenStr = JWT.create()
                    .withIssuer(issuer)
                    .withSubject(userId)
                    .withAudience(audience)
                    .withIssuedAt(now)
                    .withExpiresAt(exp)
                    .withJWTId(jti)
                    .withClaim("mc_name", this.plugin.getServer().getPlayer(userId).getName())
                    .sign(jwtAlg);
            ByteString idToken = ByteString.copyFromUtf8(idTokenStr);
            response = IDTokenProvider.MaybeId.newBuilder()
                    .setId(idToken)
                    .build();

            // DEACTIVATE (SECURITY)
            userCodeSet.remove(userCode);
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
