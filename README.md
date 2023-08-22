# BankAPI
API для создания счета и взаимодействия с ним.
## Доступные методы
- Create Account (Создание счета)
- Get Acсounts (Получение информации о счете)
- Get All Accounts (Получение всех счетов)
- Get Account's History (Получение истории счета)
## Доступные операции со счетом
- Deposit (Пополнение баланса счета)
- Withdraw (Вывод средств с баланса счета)
- Tranfer (Перевод средств на другой счет)
## Доступные URI
- /swagger-ui/index.html (Swagger docs)
- /h2-console (H2 console)
# Номер счета
Я допустил, что основная банковская инфраструктура реализована, и от API требуется лишь 7-значный идентификационный номер счета.
![Пример банковского счета](https://github.com/rthoor/BankAPI/blob/master/account.png?raw=true)
Для удобства использования по REST, исключения лишних валидаций и преобразований (Integer<->String), а также более быстрого обращения к базе данных, мною было принято решение использовать Integer в качестве идентификатора. Именно поэтому номер счета имеет вид 1XXXXXX.

