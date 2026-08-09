# DB設計書

## 概要

- DBMS: H2 Database（ファイルモード）
- 接続先: `jdbc:h2:file:./data/household-account-book`
- スキーマ生成: `spring.jpa.hibernate.ddl-auto=update`（Hibernateがエンティティ定義からテーブルを自動生成）
- 対象エンティティ: `Income`（収入）, `Expense`（支出）
- テーブル間のリレーションはなし（それぞれ独立したテーブル）

## テーブル定義書

### incomes（収入）

対応エンティティ: `com.example.entity.Income`

| No | 論理名   | 物理名        | 型            | 桁数 | PK  | NOT NULL | 備考                          |
|----|----------|---------------|---------------|------|-----|----------|-------------------------------|
| 1  | ID       | id            | BIGINT        | -    | ○   | ○        | 自動採番（IDENTITY）          |
| 2  | 収入日   | income_date   | DATE          | -    |     | ○        |                                |
| 3  | カテゴリ | category      | VARCHAR       | 50   |     | ○        |                                |
| 4  | 金額     | amount        | INTEGER       | -    |     | ○        |                                |
| 5  | メモ     | memo          | VARCHAR       | 200  |     |          | 任意項目                       |

### expenses（支出）

対応エンティティ: `com.example.entity.Expense`

| No | 論理名   | 物理名        | 型            | 桁数 | PK  | NOT NULL | 備考                          |
|----|----------|---------------|---------------|------|-----|----------|-------------------------------|
| 1  | ID       | id            | BIGINT        | -    | ○   | ○        | 自動採番（IDENTITY）          |
| 2  | 支出日   | expense_date  | DATE          | -    |     | ○        |                                |
| 3  | カテゴリ | category      | VARCHAR       | 50   |     | ○        |                                |
| 4  | 金額     | amount        | INTEGER       | -    |     | ○        |                                |
| 5  | メモ     | memo          | VARCHAR       | 200  |     |          | 任意項目                       |

## 備考

- `incomes`と`expenses`は現時点で外部キー等の関連を持たない独立テーブル。
- カテゴリはマスタテーブル化されておらず、`VARCHAR(50)`のフリーテキスト（またはアプリ側の定数）として保持。
- `ddl-auto=update`運用のため、本書はエンティティ定義（2026-08-05時点）から起こしたものであり、実スキーマの正としてはエンティティ側のコードを優先すること。
