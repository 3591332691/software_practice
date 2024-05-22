from base.BasePage import BasePage
from base import Route


class UserPage(BasePage):

    locators = {
        "BASE_ELEMENT": "view",
        "USER_INFO": "page > view.my",
    }

    def check_userPage_base_element(self):
        """
        校验页面的基本元素
        """
        # 校验页面是否包含view元素
        self.mini.assertTrue(self.mini.page.element_is_exists(UserPage.locators['BASE_ELEMENT']))
        # 校验页面是否包含用户信息元素
        self.mini.assertTrue(self.mini.page.element_is_exists(UserPage.locators['USER_INFO']))

    def check_userPage_path(self):
        """
        校验页面路径
        """
        self.mini.assertEqual(self.current_path(), Route.UserPage_route)