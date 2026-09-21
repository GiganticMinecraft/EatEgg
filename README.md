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

miseでJava 17を管理し、Gradle Wrapperを使用します。

```sh
# ビルドとテスト
mise run build

# テストのみ
mise run test
```

ビルドされたJARは`build/libs/`に出力されます。

`vX.Y.Z`形式のタグをpushすると、GitHub ActionsがJARとSHA-256チェックサムを作成してReleaseへ添付します。

## ライセンス

[GPLv3 License](./LICENSE)
