

```kotlin
 class MyPage(block: MyPage.() -> Unit) : MspView {
          init { block(this) }

          fun myView(block: MyView.() -> Unit) = MyView(block)
      }

      MyPage {
          myView {
              checkSomeAssertion()
              performSomeAction()
          }
      }
```