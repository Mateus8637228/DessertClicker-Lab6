# Лабораторная №6 — задание 3: Dessert Clicker

Основано на официальной ветке starter проекта Google Android Training.
Выручка, продажи и текущий десерт перенесены в `DessertUiState` и
`DessertViewModel`; UI получает StateFlow и отправляет события продажи во ViewModel.
Цены, пороги производства, оформление и Share сохранены.

Открыть проект отдельно в Android Studio, выбрать JDK 25 и SDK 37.

```sh
./gradlew :app:assembleDebug :app:testDebugUnitTest
./gradlew :app:connectedDebugAndroidTest
```

Unit-тесты проверяют все пороги производства; сценарий устройства — продажи,
поворот и сохранение счётчиков/цены после пересоздания Activity.
Обязательные проверки перед push: [SECURITY.md](SECURITY.md).
