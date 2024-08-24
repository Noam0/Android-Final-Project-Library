# FlippingCardView

## 🚀 A Customizable Flipping Card View Library for Android 🚀

`FlippingCardView` enables developers to implement engaging and interactive card flip animations in their Android applications. It is ideal for applications such as games, educational tools, or any scenario where card flipping enhances user interaction.

### Demo

<img src="https://github.com/user-attachments/assets/aa024633-cf8d-4f8b-8448-929a851228f7" width="260" height="430" alt="finalgif">

## Setup

### Gradle

Add the following to your project's `build.gradle` file to include `FlippingCardView` in your project:

```gradle
repositories {
    google()
    mavenCentral()
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.noam0:flippingcardlibrary:x.y.z'
}


```
## Custom Attributes

`FlippingCardView` offers several custom attributes that allow for extensive customization of the view's appearance and behavior:

### `frontImage`
- **Description**: Specifies the drawable resource used as the front image of the card.
- **Type**: Reference
- **Example**: `app:frontImage="@drawable/front_image"`
- **Usage**: Set this attribute in your XML layout to define what image appears on the front side of the card when it is facing upwards.

### `backImage`
- **Description**: Specifies the drawable resource used as the back image of the card.
- **Type**: Reference
- **Example**: `app:backImage="@drawable/back_image"`
- **Usage**: Use this attribute to set the image displayed on the back of the card. This is the image shown when the card is flipped down.

### `flipStyle`
- **Description**: Determines the style of the flip animation. This attribute enhances the interactivity of the card by defining how it flips between the front and back images.
- **Type**: Enum
### Available `flipStyle` Options:

- **rotation**: A straightforward flip without additional effects.
- **fade**: A smooth fade-out and fade-in transition between the front and back images.
- **zoomInFlip**: The card zooms in to disappear, then zooms out to reveal the other side.
- **slideRight**: The card slides to the right and flips to reveal the other side.
- **slideLeft**: The card slides to the left and flips to reveal the other side.
- **pendulumSlide**: The card swings like a pendulum before flipping.
- 
### `Example`
- `app:flipStyle="rotation"`
- `app:flipStyle="fade"`
- `app:flipStyle="zoomInFlip"`
- `app:flipStyle="slideRight"`
- `app:flipStyle="slideLeft"`
- `app:flipStyle="pendulumSlide"`
- **Usage**: This attribute allows you to choose from a variety of flip styles, making the card animation more dynamic and suited to your app's design.



## How To Use:

### Integrate `FlippingCardView` into your layout XML:

```xml
<com.example.flippingcardlibrary.FlippingCardView
    android:id="@+id/flippingCardView"
    android:layout_width="150dp"
    android:layout_height="200dp"
    app:cardRadius="10dp"
    app:flipStyle="rotation" <!-- Set the flip style here -->
    app:frontImage="@drawable/front_image"
    app:backImage="@drawable/back_image"
    android:layout_margin="8dp" />

```
<h3>Java Setup In Activity</h3>

```java

FlippingCardView flippingCardView = findViewById(R.id.flippingCardView);
flippingCardView.setFlipDuration(300); // Duration in milliseconds
flippingCardView.flipCard(); // Trigger the flip animation
```


### Customizing the Animation Duration

You can modify the duration of the flip animation using the `setFlipDuration` method. Here's an example of how to set the duration to 300 milliseconds:

```java
flippingCardView1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        flippingCardView1.setFlipDuration(300); // Set the flip duration to 300ms
        flippingCardView1.flipCard(); // Trigger the flip animation
    }
});
```
