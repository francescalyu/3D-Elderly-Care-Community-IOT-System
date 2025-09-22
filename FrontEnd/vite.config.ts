import path from 'path'
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import legacy from '@vitejs/plugin-legacy';
import Components from 'unplugin-vue-components/vite';
import AutoImport from 'unplugin-auto-import/vite';
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers';
import tailwindcss from '@tailwindcss/vite'
import topLevelAwait from 'vite-plugin-top-level-await';

export default defineConfig({
  base: './', // 确保打包后的资源路径是相对路径
  server: {
    host: true,
    port: 9099,
    // proxy:{
    //   // '/foo': 'http://localhost:9099',
    //   '/api':{
    //   target:'http://101.43.122.193:9099/api',
    //   changeOrigin: true,
    //   rewrite:(path)=>path.replace(/^\/api/,''),
    //   },
    // }
  },
  build: {
    reportCompressedSize: false,
    /** 设置为一个特定的浏览器版本数组。这允许你根据项目需求和用户群体定制支持的浏览器环境。**/
    // target: 'esnext',
    /** 消除打包大小超过 500kb 警告 */
    chunkSizeWarningLimit: 2000,
  },
  css: {
    preprocessorOptions: {
      // 如果'modern-compiler'不管用，可换成"modern"
      scss: {
        api: 'modern-compiler' // or "modern"
      }
    }
  },
  plugins: [
    vue(),
    tailwindcss(),
    Components({
      resolvers: [ElementPlusResolver()], // 以Ant Design Vue为例
    }),
    AutoImport({
      resolvers: [ElementPlusResolver()],
      imports: ['vue', 'vue-router'], // 自动导入Vue和Vue Router的APIs
      dts: 'auto-imports.d.ts', // 生成类型声明文件
    }),
    legacy({
      targets: ['defaults', 'not IE 11'],
      additionalLegacyPolyfills: ['regenerator-runtime/runtime']
    }),
    topLevelAwait()
  ],
  //设置路径别名
  resolve: {
    alias: [
      { find: '/^~/', replacement: '' },
      { find: '@', replacement: path.resolve(__dirname, 'src') },
    ],
  },
});