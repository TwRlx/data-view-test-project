# DataViewTestProject
Тестовый проект отбражающий список фотографий с сервиса http://jsonplaceholder.typicode.com/photos
Изначально загружается список альбомов http://jsonplaceholder.typicode.com/albums. Затем загружаются фотографии из первого альбома http://jsonplaceholder.typicode.com/albums/1/photos и отображаются в RecyclerView фрагмента PhotosListFragment. При прокрутке вниз подгружаются фотографии из других альбомов http://jsonplaceholder.typicode.com/albums/{id}/photos. При обновлении список очищается и в него снова загружаются фотографии из первого альбома.
## Apk file
https://drive.google.com/file/d/0B6iyDPTEohVmZXlfZ1djZ2tpOUU/view

Предыдущую версию нужно удалить (там был debug-apk, а сейчас release)
## Основные пакеты
**.adapters** Содержит адаптер PhotosAdapter для RecyclerView списка фотографий в фрагменте PhotosListFragment

**.annotaions** Аннотации для компонент и полей в Dagger2.

**.di** Содержит компоненту приложения DataViewComponent с доступом к Retrofit и connection IntentFilter, и компоненту для фрагмента PhotoListFragment с сетевым модулем PhotosApiModule и модулем фрагмента PhotosListModule.

**.fragments** Содержит фрагменты PhotosListFragment и PhotoDetailsFragment.

**.network** Содержит PhotosApi для работы с запросами к http://jsonplaceholder.typicode.com/photos

**.units** Содержит объекты альбома(Album) и фотографии(AlbumPhoto)

**.utils** Содержит NetworkUtils с возможностью проверки интернет соединения и CacheInterceptor для настройки работы OkHttp с кэшем.

**.views** Содержит EndlessRecyclerOnScrollListener для реализации списка с ленивой подгрузкой.

## Основные классы
#### MainActivity.

*Управляет взаимодействием фрагментов PhotoDetailsFragment и PhotoListFragment*
#### PhotoDetailsFragment.

*Фрагмент с детальным отображением одного фото*
#### PhotoListFragment.

*Фрагмент c фотографиями пользователя в виде списка. Отправляет запросы на получение альбомов и фотографий в альбоме. RecylcerView обёрнут в SwipeRefreshLayout для возможности обновления при свайпе вниз и имеет слушателя EndlessRecyclerOnScrollListener на прокрутку вниз для реализации ленивой подгрузки. К фрагменту регистрируется BroadcastReciever для отслеживания состояния интернета, который включает/выключает возможность свайпа.*

## Используемые библиотеки

**glide** Для загрузки изображений

**dagger2**

**recyclerView**

**retrofit2** Для работы с запросами

**appcompat**

minSdk 15

targetSdk 23

compileSdk 23
