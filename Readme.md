# ServerBuild

> 此插件将大多数小插件整合成在一起，并在原基础上开放更多可自定义设置.
>
> 插件的每个功能都由独立文本控制，避免了功能杂糅的情况. 当在 `Config.yml` 文件中启用了全局配置，其他功能的设置都将同步.
>
> 未开启相应功能也不会占用其他插件指令，我的意思是此插件可以和cmi, ess之类的插件搭配使用. 插件自动检测文件更改，可以无需用指令重载(不需要自动检测可在 FileMonitor 关闭此功能).

## 重构进度

> 重构过程中可能新增更多功能

- [x] AutoReSpawn
- [ ] BookContent
- [x] ChatFormat
- [x] ResolveLag
- [x] FileMonitor
- [ ] Home
- [ ] LoginTP
- [ ] MessageAnnouncer
- [ ] NotBuild
- [ ] PlayerShout
- [ ] PlayerEdit
- [ ] Scoreboard
- [ ] Teleport
- [x] WelcomeMessage

## 功能

| 英文名           | 中文名         | 描述                                                         |
| ---------------- | -------------- | ------------------------------------------------------------ |
| AutoReSpawn      | 自动重生       | 自动重生玩家.<br />Auto re spawn player.                     |
| BookContent      | 书本文章       | 生成一个带文章的书本<br />Give a book whit articles.         |
| ChatFormat       | 聊天格式       | 更改游戏原版聊天格式<br />Replace minecraft default chat message format. |
| ResolveLag       | 服务器清理     | 清理服务器实体及区块<br />Clear server's entities and chunks. |
| FileMonitor      | 文件检测       | 检测文件变动并自动重载对应功能(默认开启)<br />Auto reload module when the file changed. |
| LoginTP          | 固定登录点     | 固定玩家每次上线位置<br />Fixed player login server location. |
| MessageAnnouncer | 公告           | 定时公告<br />Auto message.                                  |
| NotBuild         | 禁止建筑       | 禁止建筑<br />Prohibit player from building.                 |
| PlayerShout      | 喊话           | 玩家喊话<br />Player shout.                                  |
| Scoreboard       | 计分板         | 玩家计分板<br />new Scoreboard                               |
| Teleport         | 传送           | 传送<br />Player teleport                                    |
| WelcomeMessage   | 服务器欢迎消息 | 更改原版玩家进入/退出服务器消息格式<br />Replace minecraft default login/quit message format. |

## 配置文件

暂无

## 权限

暂无

## 指令

暂无



## 附属开发

插件提供了[ServerBuild-Core](https://github.com/hanhan2001/ServerBuild-Core)，可以通过ServerBuild-Core调用ServerBuild接口.

ServerBuild-Core提供了线程、文件、命令、监听器的处理方案，可以自动处理线程和监听器，从而避免出现创建多个线程或监听器导致插件问题。

在我的设想中应该可以通过plugin.yml设置ServerBuild-Core的加载主类，不过现在还没实现(懒)