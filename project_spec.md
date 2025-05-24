# Spring Boot プロジェクト仕様書

## プロジェクト概要
- Spring Boot（Gradle）を利用したWebアプリケーション
- Java 17を使用
- VSCodeからデバッグ・実行可能

## 実装済み機能

### 1. multipart/form-data対応のPOST API
- エンドポイント: `/upload`
- メソッド: POST
- Content-Type: multipart/form-data
- パラメータ:
    - `file`: アップロードするファイル
    - `description`: 任意のテキスト説明（省略可）
- レスポンス:
    - ファイル名、サイズ、説明、内容（先頭100文字）をテキストで返却
- 実装クラス: `MultipartController`

### 2. リクエスト・レスポンス内容のログ出力フィルター
- 全リクエスト・レスポンスの内容（メソッド、パス、パラメータ、ステータス）をログ出力
- 実装クラス: `LoggingFilter`
- ログはANSIカラーで出力（`spring.output.ansi.enabled=ALWAYS`）

### 3. VSCode用の起動・デバッグ設定
- `.vscode/launch.json` で `DemoApplication` を直接起動・デバッグ可能
- `.vscode/settings.json` でJava 17（openjdk@17）をデフォルトJDKに指定

## その他
- `build.gradle` で `sourceCompatibility`/`targetCompatibility` を 17 に設定
- Gitでバージョン管理

---
このファイルはAI間で仕様共有・追加要件整理などに利用できます。 