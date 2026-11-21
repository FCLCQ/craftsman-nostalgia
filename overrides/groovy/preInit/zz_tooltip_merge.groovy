// 匠魂把挖掘等级名称追加成单独一行，这里在提示框生成后合并回同一行
import net.minecraftforge.event.entity.player.ItemTooltipEvent

event_manager.listen { ItemTooltipEvent event ->
    def tooltip = event.getToolTip()
    for (int i = 0; i < tooltip.size(); i++) {
        String line = tooltip.get(i)
        if (line != null && line.contains('挖掘等级')) {
            log.info('提示框行：' + line)
        }
    }
    for (int i = 0; i < tooltip.size() - 1; i++) {
        String line = tooltip.get(i)
        if (line != null && line.trim() == '挖掘等级:') {
            tooltip.set(i, line + ' ' + tooltip.get(i + 1))
            tooltip.remove(i + 1)
            break
        }
    }
}
