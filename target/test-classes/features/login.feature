Feature: 登录功能
  Scenario: first Login as reader
    # 在首次以读者身份登录时，预期的行为是创建一个新的读者账户，并且不会对作者数据库进行任何更改
    Given I never login in as reader
    When I click the reader_login button
    And I authorized my wechat account
    Then A new reader account is created successfully and author sql no change

  Scenario: first Login as author
    # 在首次以作者身份登录时，预期的行为是创建一个新的作者账户，并且不会对读者数据库进行任何更改
    Given I never login in as author
    When I click the author_login button
    And I authorized my wechat account
    Then A new author account is created successfully and reader sql no change

  Scenario: not first Login as reader
    # 在非首次以读者身份登录时，预期的行为是不会创建新的账户，并且不会对作者数据库进行任何更改
    Given I've logged in as reader
    When I click the reader_login button
    And I authorized my wechat account
    Then reader sql and author sql no change

  Scenario: not first Login as author
    # 在非首次以作者身份登录时，预期的行为是不会创建新的账户，并且不会对读者数据库进行任何更改
    Given I've logged in as author
    When I click the author_login button
    And I authorized my wechat account
    Then reader sql and author sql no change

  Scenario: Login as admin successfully
    # 以管理员身份输入正确密码时，预期的行为登录管理员账户成功
    Given I know the password
    When I click the admin_login button
    And I enter the right password
    Then Login successfully

  Scenario: Login as admin failure
    # 以管理员身份输入错误密码时，预期的行为是登录管理员账户失败
    Given I don't know the password
    When I click the admin_login button
    And I enter the wrong password
    Then Login fail