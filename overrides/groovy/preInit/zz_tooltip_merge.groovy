// 匠魂的挖掘等级行用 "%s: %s" 拼接，冒号后的空格让整行变宽后被折行。
// 提示框生成后把这里的 ": " 压成 ":"，让整行落回单行。
import net.minecraftforge.event.entity.player.ItemTooltipEvent

event_manager.listen { ItemTooltipEvent event ->
    def tooltip = event.getToolTip()
    for (int i = 0; i < tooltip.size(); i++) {
        String line = tooltip.get(i)
        if (line != null && line.contains('挖掘等级')) {
            String fixed = line.replace(': ', ':').replace('： ', '：')
            if (fixed != line) {
                tooltip.set(i, fixed)
                log.info('提示框行压缩：' + fixed)
            }
        }
    }
}
