# IMUbackendPlugin

インモラル大学のWebサービス第2世代のマイクラサーバ側バックエンドです。

[インモラル大学WebサービスのREADME](https://github.com/skpub/IMUweb2)

認証部分とその他機能からなります。

## 認証部分

1. プレイヤーがマイクラサーバにログインしたらフロントエンドへのURL(ユーザのUUIDつき)と一時パスコードを提示
   * UUIDとパスコードをサーバで保存(3分間だけ)
2. ユーザがアクセスしてフロントエンドに一時パスコードを入力して送信
   * 保存されているUUIDとパスコードに一致するならばID token (JWT (HMAC256))を発行

## 必要な環境変数

|環境変数|内容|
|---|---|
|ID_TOKEN_PROVIDER_SECRET|ID Token生成秘密鍵|
|IMU_ID_ISSUER|認証サーバ名(この際IMUserver)|
|IMU_WEBAPP|リソースサーバ名(この際IMUwebapp)|

## ライセンス表記

- **java-jwt** (MIT License)
  - [Maven Repository](https://mvnrepository.com/artifact/com.auth0/java-jwt)
  - Copyright (c) 2015 Auth0, Inc.

- **ZXing (javase)** (Apache License 2.0)
  - [Maven Repository](https://mvnrepository.com/artifact/com.google.zxing/javase)
  - Copyright (c) 2007 ZXing authors

- **Shadow Gradle Plugin** (Apache License 2.0)
  - [Maven Repository](https://mvnrepository.com/artifact/com.github.johnrengelman.shadow/com.github.johnrengelman.shadow.gradle.plugin)
  - Copyright (c) 2012-2024 John Rengelman

- **Protobuf Gradle Plugin** (Apache License 2.0)
  - [Maven Repository](https://mvnrepository.com/artifact/com.google.protobuf/com.google.protobuf.gradle.plugin)
  - Copyright (c) 2013 Google LLC

- **gRPC Java** (Apache License 2.0)
  - [Maven Repository](https://mvnrepository.com/artifact/io.grpc/grpc-netty-shaded)
  - Copyright (c) 2015 The gRPC Authors

- **Protobuf Java** (BSD 3-Clause License)
  - [Maven Repository](https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java)
  - Copyright (c) 2008 Google Inc.

- **Paper API** (MIT License)
  - [Maven Repository](https://repo.papermc.io/public/io/papermc/paper-api/)
  - Copyright (c) PaperMC contributors  

