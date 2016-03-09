KindEditor.plugin('menu', function(K) {
        var editor = this, name = 'menu';
        // 点击图标时执行
        editor.clickToolbar(name, function() {
                editor.insertHtml('<a href="../index.html">返回目录</a>');
        });
});