# AWS S3 カスタム機能ドキュメント

このドキュメントは、AWS S3 のカスタム機能が提供する、
Flow、コマンド、設定定義についての説明及び出力するメッセージの定義について説明する。

- Contents
  - [Information](#Information)
  - [Description](#Description)
  - [Flow List](#Flow-List)
  - [Command List](#Command-List)
  - [Configuration List](#Configuration-List)
  - [Message List](#Message-List)

## Information

本カスタム機能の基本情報は以下の通り。

AWSのS3へのアクセスを行う機能を提供します。

- Name : `aws-s3`
- Custom Package : `com.epion_t3.aws.s3`

## Description
AmazonWebService（AWS）のSimpleStorageService（S3）への各種アクセスを行う機能を提供します。事前データの配置、エビデンスの取得などに利用できるPUT、GETを代表とした機能です。

## Flow List

本カスタム機能が提供するFlowの一覧及び詳細。

|Name|Summary|
|:---|:---|


## Command List

本カスタム機能が提供するコマンドの一覧及び詳細。

|Name|Summary|Assert|Evidence|
|:---|:---|:---|:---|
|[AwsS3PutObject](#AwsS3PutObject)|AWS S3にオブジェクトを登録します。  |||
|[AwsS3PutObjects](#AwsS3PutObjects)|AWS S3に指定されたディレクトリ配下のオブジェクトを登録します。  |||
|[AwsS3DeleteObjects](#AwsS3DeleteObjects)|AWS S3からオブジェクトリストを削除します。  ||X|
|[AwsS3ListObjects](#AwsS3ListObjects)|AWS S3からオブジェクトリストを取得します。  ||X|
|[AwsS3DeleteObject](#AwsS3DeleteObject)|AWS S3からオブジェクトを取得します。  |||
|[AwsS3GetObject](#AwsS3GetObject)|AWS S3からオブジェクトを取得します。  ||X|

------

### AwsS3PutObject
AWS S3にオブジェクトを登録します。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- AWS S3からオブジェクトを取得します。
- AWS S3から同じオブジェクトを取得した場合、別エビデンスとして取得します。
- このコマンドが動作するには、AWSへの接続設定（ [AwsCredentialsProviderConfiguration](https://github.com/epion-tropic-test-tool/epion-t3-aws-core/blob/master/aws-core_spec_ja_JP.md#awscredentialsproviderconfiguration) ）が必要になります。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「AwsS3PutObject」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  credentialsConfigRef : 資格設定の参照
  sdkHttpClientConfigRef : HTTPクライアント設定の参照
  target : 登録対象のファイルの相対パス
  bucket : s3のBucket
  key : s3のKey

```

------

### AwsS3PutObjects
AWS S3に指定されたディレクトリ配下のオブジェクトを登録します。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- AWS S3へ指定されたディレクトリ配下のオブジェクト（ファイル）を登録します。
- 指定されたディレクトリ直下のファイルのみを対象とします。配下全てのPUTには対応していません。
- このコマンドが動作するには、AWSへの接続設定（ [AwsCredentialsProviderConfiguration](https://github.com/epion-tropic-test-tool/epion-t3-aws-core/blob/master/aws-core_spec_ja_JP.md#awscredentialsproviderconfiguration) ）が必要になります。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「AwsS3PutObjects」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  credentialsConfigRef : 資格設定の参照
  sdkHttpClientConfigRef : HTTPクライアント設定の参照
  target : 登録対象のファイルの相対パス
  bucket : s3のBucket
  prefix : s3のPrefix

```

------

### AwsS3DeleteObjects
AWS S3からオブジェクトリストを削除します。
#### Command Type
- Assert : No
- Evidence : __Yes__

#### Functions
- AWS S3からオブジェクトリストを削除します。
- このコマンドが動作するには、AWSへの接続設定（ [AwsCredentialsProviderConfiguration](https://github.com/epion-tropic-test-tool/epion-t3-aws-core/blob/master/aws-core_spec_ja_JP.md#awscredentialsproviderconfiguration) ）が必要になります。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「AwsS3DeleteObjects」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  credentialsConfigRef : 資格設定の参照
  sdkHttpClientConfigRef : HTTPクライアント設定の参照
  bucket : s3のBucket
  prefix : s3のPrefix

```

------

### AwsS3ListObjects
AWS S3からオブジェクトリストを取得します。
#### Command Type
- Assert : No
- Evidence : __Yes__

#### Functions
- AWS S3からオブジェクトリストを取得します。
- このコマンドが動作するには、AWSへの接続設定（ [AwsCredentialsProviderConfiguration](https://github.com/epion-tropic-test-tool/epion-t3-aws-core/blob/master/aws-core_spec_ja_JP.md#awscredentialsproviderconfiguration) ）が必要になります。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「AwsS3ListObjects」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  credentialsConfigRef : 資格設定の参照
  sdkHttpClientConfigRef : HTTPクライアント設定の参照
  bucket : s3のBucket
  prefix : s3のPrefix

```

------

### AwsS3DeleteObject
AWS S3からオブジェクトを取得します。
#### Command Type
- Assert : No
- Evidence : No

#### Functions
- AWS S3からオブジェクトを削除します。
- このコマンドが動作するには、AWSへの接続設定（ [AwsCredentialsProviderConfiguration](https://github.com/epion-tropic-test-tool/epion-t3-aws-core/blob/master/aws-core_spec_ja_JP.md#awscredentialsproviderconfiguration) ）が必要になります。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「AwsS3DeleteObject」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  credentialsConfigRef : 資格設定の参照
  sdkHttpClientConfigRef : HTTPクライアント設定の参照
  bucket : s3のBucket
  key : s3のKey

```

------

### AwsS3GetObject
AWS S3からオブジェクトを取得します。
#### Command Type
- Assert : No
- Evidence : __Yes__

#### Functions
- AWS S3からオブジェクトを取得します。
- AWS S3から同じオブジェクトを取得した場合、別エビデンスとして取得します。
- このコマンドが動作するには、AWSへの接続設定（ [AwsCredentialsProviderConfiguration](https://github.com/epion-tropic-test-tool/epion-t3-aws-core/blob/master/aws-core_spec_ja_JP.md#awscredentialsproviderconfiguration) ）が必要になります。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「AwsS3GetObject」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  credentialsConfigRef : 資格設定の参照
  sdkHttpClientConfigRef : HTTPクライアント設定の参照
  bucket : s3のBucket
  key : s3のKey

```


## Configuration List

本カスタム機能が提供する設定定義の一覧及び詳細。

|Name|Summary|
|:---|:---|


## Message List

本カスタム機能が出力するメッセージの一覧及び内容。

|MessageID|MessageContents|
|:---|:---|
|com.epion_t3.aws.s3.err.9003|対象（target）のバケット及びプレフィックスは存在しません.Bucket:{0}, Prefix:{1}|
|com.epion_t3.aws.s3.err.9004|対象（target）はディレクトリを指定する必要があります. Target:{0}|
|com.epion_t3.aws.s3.err.9001|対象（target）のバケット及びキーへアクセスできません.Bucket:{0}, Path:{1}|
|com.epion_t3.aws.s3.err.9002|対象（target）のバケット及びキーは存在しません.Bucket:{0}, Path:{1}|
