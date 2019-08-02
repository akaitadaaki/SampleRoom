# SampleRoom

Android ViewModel and DataBinding and Room のサンプルです。  
* http://project.iw3.org/zip_search_x0401/index.html のAPIを利用して郵便番号から住所を取得します。
* 取得した住所は Room Database を利用して保存しており、次回以降は Room Database から取得して表示します。
* 値の取得はや出力は DataBinding にまかせています。
* 本来は coroutine を利用して非同期させるべきですが、今回は coroutine なしで作成しています。
* このプログラムはviewmodelのsavedstate未対応など、不完全です。
