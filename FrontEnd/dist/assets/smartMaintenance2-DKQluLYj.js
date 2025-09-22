import { _ as t, c as r, o, a as e, __tla as __tla_0 } from "./index-Ds3GgQ3n.js";
let i;
let __tla = Promise.all([
  (() => {
    try {
      return __tla_0;
    } catch (e2) {
    }
  })()
]).then(async () => {
  const s = {};
  function a(c, n) {
    return o(), r("div", null, n[0] || (n[0] = [
      e("h1", null, "smartSecurity 2", -1),
      e("p", null, "This is the first workbench view.", -1),
      e("p", null, "Here you can add your content.", -1),
      e("p", null, "Feel free to customize it as needed.", -1)
    ]));
  }
  i = t(s, [
    [
      "render",
      a
    ]
  ]);
});
export {
  __tla,
  i as default
};
