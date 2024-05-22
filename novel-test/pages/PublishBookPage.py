from base.BasePage import BasePage
from base import Route


class PublishBookPage(BasePage):
    
    locators = {
        "BASE_ELEMENT": "view",
        "FORM": "page > view > view.form",
    }

    def check_publishBookPage_base_element(self):
        """
        校验页面的基本元素
        """
        # 校验页面是否包含view元素
        self.mini.assertTrue(self.mini.page.element_is_exists(PublishBookPage.locators['BASE_ELEMENT']))
        # 校验页面是否包含表单
        self.mini.assertTrue(self.mini.page.element_is_exists(PublishBookPage.locators['FORM']))

    def check_publishBookPage_path(self):
        """
        校验页面路径
        """
        self.mini.assertEqual(self.current_path(), Route.PublishBookPage_route)