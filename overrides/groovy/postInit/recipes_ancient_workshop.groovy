crafting.removeByOutput(item('tconstruct:smeltery_controller'))

crafting.shapedBuilder()
        .name('smeltery_controller_with_copper')
        .output(item('tconstruct:smeltery_controller'))
        .shape([[ore('ingotBrickSeared'), ore('ingotBrickSeared'), ore('ingotBrickSeared')],
                [ore('ingotBrickSeared'), ore('ingotCopper'), ore('ingotBrickSeared')],
                [ore('ingotBrickSeared'), ore('ingotBrickSeared'), item('tconstruct:materials')]])
        .register()
