# EatEgg

卵やスポーンエッグによる意図しないエンティティのスポーンを抑制するPaperプラグインです。

## 動作環境

- Paper API `1.18.2-R0.1-SNAPSHOT`
- WorldGuard
- WorldEdit
- Java 17

## 導入

1. [Releases](../../releases)から`EatEgg`のJARファイルを取得します。
2. WorldGuardとWorldEditをPaperサーバーへ導入します。
3. JARファイルをPaperサーバーの`plugins/`ディレクトリへ配置します。
4. サーバーを起動または再起動します。

## 使い方

有効化したワールドでは、通常の卵を投げてもニワトリがスポーンせず、スポーンエッグを使用できません。
WorldGuardのリージョン所有者は制限を受けません。

`/eategg toggle`を実行すると、自分だけ制限を一時的にバイパスできます。
この状態はプレイヤーの退出時にリセットされます。

## コマンドと権限

- `/eategg reload` — 設定を再読込します。`eategg.reload`（デフォルト: op）
- `/eategg toggle` — 制限の有効・無効を切り替えます。`eategg.toggle`（デフォルト: op）

## 設定

```yaml
# 有効化するワールドのリスト。大文字小文字は区別されません。
enabled-worlds:
  - world
  - world_nether
```

## 開発

### miseを使う場合

miseをインストールしたうえで、プロジェクトのルートディレクトリから実行します。

```shell
# 初回のみ
mise install

# ビルド
mise run build

# テストのみ
mise run test

# YAML lint
mise run lint-yaml

# Markdown lint
mise run lint-markdown

# GitHub Actions security scan
mise run lint-gha

# Renovate設定の検証
mise run validate-renovate
```

Windowsでも同じmiseタスクを使用できます。

ビルドされたJARは`build/libs/`に出力されます。

### miseを使わない場合

JDK 17を別途用意し、`JAVA_HOME`または`PATH`を設定してください。YAML lintにはPythonの仮想環境を使用します。

```shell
# 初回のみ
python3 -m venv .venv
. .venv/bin/activate
python -m pip install yamllint==1.37.1

# ビルド
./gradlew build

# テストのみ実行
./gradlew test

# YAML lint
python -m yamllint --format github --config-file \
  .github/.yamllint.yaml .github src/main/resources
```

Windowsでは、`py -m venv .venv`で仮想環境を作成します。
`.venv\Scripts\activate`で有効化し、Gradleには
`gradlew.bat build`または`gradlew.bat test`を使用します。

## リリース

リリースは、署名付き`vX.Y.Z`タグのpushを起点にGitHub Actionsが実行します。

リリース担当者は、署名鍵が設定された環境で次を実行します。
`mise add-tag`と`mise push-tag`は、Git backendが有効なcheckoutで実行してください。

```shell
# 署名付きタグを作成
mise add-tag 1.0.0

# タグをpushしてリリース処理を開始
mise push-tag 1.0.0
```

タグのpush後、Java 17でのビルド・テスト、JARのSHA-256生成、GitHub artifact attestation、Draft Releaseの作成・公開を行います。
公開されたReleaseはImmutableになり、JARとチェックサムは変更できません。

Releaseには次のファイルが添付されます。

- `EatEgg-v<version>.jar`
- `EatEgg-v<version>.jar.sha256`

JARの検証には次を使用できます。

```shell
sha256sum -c EatEgg-v1.0.0.jar.sha256
gh attestation verify EatEgg-v1.0.0.jar -R GiganticMinecraft/EatEgg
gh release verify v1.0.0 -R GiganticMinecraft/EatEgg
```

## CI

Pull Requestでは次のチェックが実行されます。

ビルド・lint・設定検証は、開発時と同じmiseタスクを使用します。

- Java 17でのGradleビルド・テスト
- Markdown lint
- YAML lint（GitHub Actionsと`plugin.yml`）
- GitHub Actionsのセキュリティ検査
- Renovate設定の検証

## ライセンス

[GPLv3 License](./LICENSE)
