Feature: 收藏功能
  Scenario: 第一次将此书收藏到书架
    # 第一次将一本书收藏到书架的场景
    Given this book not in my bookshelf
    When I click the Add_to_bookshelf button
    Then this book in my bookshelf

  Scenario: 非第一次将此书收藏到书架
    # 将已经收藏的书再次收藏到书架的场景,应显示警告消息，指示该书已经在书架上，并且书架应保持不变
    Given this book in my bookshelf
    When I click the Add_to_bookshelf button
    Then return warn this book already on my bookshelf and bookshelf no change
