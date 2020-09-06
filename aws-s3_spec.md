#  Command

- Contents
  - [Information](#Information)
  - [Description](#Description)
  - [Flow List](#Flow-List)
  - [Command List](#Command-List)
  - [Configuration List](#Configuration-List)
  - [Message List](#Message-List)


## Information
AWSのS3へのアクセスを行う機能を提供します。

- Name : `aws-s3`
- Custom Package : `com.epion_t3.aws.s3`

## Description
AmazonWebService（AWS）のSimpleStorageService（S3）への各種アクセスを行う機能を提供します。事前データの配置、エビデンスの取得などに利用できるPUT、GETを代表とした機能です。

## Flow List

## Command List

|Name|Summary|Assert|Evidence|
|:---|:---|:---|:---|
|[AwsS3Get](#AwsS3Get)|AWS S3からオブジェクトを取得します。  ||X|

------

### AwsS3Get
AWS S3からオブジェクトを取得します。
#### Command Type
- Assert : No
- Evidence : __Yes__

#### Functions
- AWS S3からオブジェクトを取得します。
- AWS S3から同じオブジェクトを取得した場合、別エビデンスとして取得します。
- このコマンドが動作するには、AWSへの接続設定（Configuration）が必要になります。

#### Structure
```yaml
commands : 
  id : コマンドのID
  command : 「AwsS3Get」固定
  summary : コマンドの概要（任意）
  description : コマンドの詳細（任意）
  bucket : s3のBucket
  key : s3のKey

```


## Configuration List

## Message List

 Command output messages.

|MessageID|MessageContents|
|:---|:---|
|com.epion_t3.aws.s3.err.9001|対象（target）のバケット及びキーへアクセスできません.Bucket:{0}, Path:{1}|
|com.epion_t3.aws.s3.err.9002|対象（target）のバケット及びキーは存在しません.Bucket:{0}, Path:{1}|
