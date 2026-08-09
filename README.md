# 家計簿アプリ

収入と支出を登録できる、シンプルな家計簿アプリです。

登録画面では、日付・カテゴリ・金額・メモを入力できます。入力内容のチェックを行い、問題がなければローカルのH2データベースに保存します。

## ドキュメント

DB設計書は `docs` 配下に配置しています。

- [テーブル定義書](docs/database/table-definition.md)

## 起動手順

### 必要な環境

- Java 21
- Maven

### 起動方法

1. PowerShellでこのプロジェクトのディレクトリを開きます。

2. 次のコマンドを実行します。

```powershell
mvn spring-boot:run
```

3. 起動後、ブラウザで次のURLを開きます。

```text
http://localhost:8080/
```

4. 終了するときは、PowerShellで `Ctrl + C` を押します。
