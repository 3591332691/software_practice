from pathlib import Path

import minium


class BaseCase(minium.MiniTest):
    """测试用例基类"""

    @classmethod
    def setUpClass(cls):
        super(BaseCase, cls).setUpClass()
        output_dir = Path(cls.CONFIG.outputs)
        if not output_dir.is_dir():
            output_dir.mkdir()

    @classmethod
    def tearDownClass(cls):
        super(BaseCase, cls).tearDownClass()
        cls.app.go_home()

    def setUp(self):
        super(BaseCase, self).setUp()

    def tearDown(self):
        super(BaseCase, self).tearDown()