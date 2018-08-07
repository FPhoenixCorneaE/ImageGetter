# ImageGetter
TextView set Html string with img and listening img clicked event.

预览图片：
=========================
![预览图片](https://github.com/FPhoenixCorneaE/ImageGetter/blob/master/ImageGetter/image/preview.jpg)


代码中使用：
=========================
```
    private String mHtmlString = "<div><img src=\"http://img2.imgtn.bdimg.com/it/u=3339115819,2775910367&fm=27&gp=0.jpg\">唯美香山红叶</div>" +
            "<div>秦时明月少司命<img src=\"http://p.store.itangyuan.com/p/chapter/attachment/Eg-vEBjWES/EgfVEgAtet6UEtAveB6vEluwEvDrjNbwjhDOfv5HIvH24gi6ihIT662.jpg\"></div>" +
            "<div>游戏原画<img src=\"http://img3.imgtn.bdimg.com/it/u=56678442,4260990607&fm=27&gp=0.jpg\"></div>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ResourceUtils.getLayoutId("activity_main"));
        initView();
    }

    private void initView() {

        TextView mTvContent = (TextView) findViewById(ResourceUtils.getId("tv_content"));

        HtmlImageGetterHelper.getHelper().setHtmlString(mTvContent, mHtmlString);

    }
```
