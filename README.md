# EatEgg

卵やスポーンエッグによる意図しないエンティティのスポーンを抑制するSpigotプラグインです。
卵は投げられますが、鶏がスポーンしなくなります。スポーンエッグは使用自体ができなくなります。

## Commands and permissions

| Command          | Description                 | Permission      | Default Permission |
|------------------|-----------------------------|-----------------|--------------------|
| `/eategg reload` | 設定を再読込します。                  | `eategg.reload` | op                 |
| `/eategg toggle` | 卵やスポーンエッグを投げられるかどうかを切り替えます。 | `eategg.toggle` | op                 |

## Config(config.yml)

```yaml
# 有効化するワールドのリスト。大文字小文字は区別されません。
enabled-worlds:
  - world
  - world_nether
```

## License

[MIT License](./LICENSE)
