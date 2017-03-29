# RubberIndicator


[原项目地址](https://github.com/LyndonChin/AndroidRubberIndicator)

原来的控件很不完善，只能够修改三个颜色，且实现的效果与效果图不太相符，
我的目标是开放更多的可配置项并且达到根据高度等比例缩放的效果。


A rubber indicator for ViewPager

<img src="https://github.com/hkq325800/AppRelease/blob/master/art/AndroidRubberIndicator_1.gif?raw=true" width="270px" height="480px" />

* Designed by [Valentyn Khenkin](https://dribbble.com/shots/2090803-Rubber-Indicator?list=searches&tag=indicator&offset=7)
* [Here](http://codepen.io/machycek/full/eNvyjb/) is the CSS version

## Usage 

The attributes for `RubberIndicator` are not yet finished.
A toy example is provided in [sample](sample/src/main/java/com/liangfeizc/rubberindicator/MainActivity.java).

## Introduction

APIs offered by **RubberIndicator**.

|APIs | Usage|
|---|---|
|setCount(int count)|Set the count of indicators|
|setCount(int count, int focusPos)|Set the count and specify the focusing indicator|
|setFocusPosition(int pos)|Set focusing indicator|
|getFocusPosition()|Get focusing indicator|
|moveToLeft()|Move the focusing indicator to left|
|moveToRight()|Move the focusing indicator to right|

In addition to the APIs listed in the table, **RubberIndicator** also provides a callback listener - **OnMoveListener** for the user should be notified when the moving animator finished.

```java
public interface OnMoveListener {
	void onMovedToLeft();
	void onMovedToRight();
}
```

## License

    MIT
