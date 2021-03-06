#language: ru

@firstTest
Функционал: Страхование путешественников
#compile -Dmaven.test.skip=true
  @firstTest
  Сценарий: Ипотека на готовое жилье и заполнение полей
    * Закрытия сообщения cookies
    * Выбираем "Ипотека" в главном меню
    * Выбираем "Ипотека на вторичное жильё" в подменю главного меню
    * На вкладке Оформить заполнить поля:
      | Стоимость недвижимости | 5180000 |
      | Первоначальный взнос   | 3058000 |
      | Срок кредита           | 30      |
    * Поставить галочку с поля "Страхование жизни и здоровья"
    * Подождать загрузку данных
    * Проверяем заполнение полей
      | Сумма кредита      | 2122000 |
      | Ежемесячный платеж | 8057    |
      | Необходимый доход  | 22711   |
      | Процентная ставка  | 11      |



