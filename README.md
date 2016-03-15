# DataViewTestProject
Тестовый проект отбражающий список фотографий с сервиса http://jsonplaceholder.typicode.com/photos
## Apk file
https://drive.google.com/file/d/0B6iyDPTEohVmZXlfZ1djZ2tpOUU/view
## Main classes
* MainActivity. Управляет взаимодействием фрагментов PhotoDetailsFragment и PhotoListFragment
* PhotoDetailsFragment.
Фрагмент с детальным отображением одного фото
* PhotoListFragment.
Фрагмент c фотографиями пользователя в виде списка. При старте фрагмента делается запрос на получение фотографий (http://jsonplaceholder.typicode.com/photos). Если интернет соединение отсутствует, то отобразяться данные, которые были сохранены в кэше.  Для обновления списка фотографий нужно потянуть вниз список. 
