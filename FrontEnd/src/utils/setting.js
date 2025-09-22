// settings.js

export const settings = {
    title: '长沙南综合节能管理控制系统',
    /**
     * @type {String}
     * @description 接口报错的默认提示
     */
    serviceErrorText: '服务器错误，请稍后重试！',
    /**
     * @type {Object}
     * @description 颜色变量
     */
    theme: {
        blue: {
            // theme
            defaultText: '#FFFFFF',
        },

        white: {
            // theme
            defaultText: '#000000',
        },

        grey: {}
    },

    /**
     * @type {String}
     * @description 主题默认类型
     */
    themeType: 'white',

    /**
     * @type {String}
     * @description stomp websocket 的 认证
     */
    stompWebSocket: {
        login: 'guest',
        passcode: 'guest'
    }
};

export const stompWebSocket = settings.stompWebSocket;
