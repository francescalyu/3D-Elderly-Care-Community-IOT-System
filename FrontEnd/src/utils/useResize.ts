// useResize.js

export default function useResize(chartRef) {
  const sidebarElm = ref<any>(null);

  const debounce = (func, wait, immediate) => {
    let timeout, args, context, timestamp, result;

    const later = function () {
      const last = +new Date() - timestamp;
      if (last < wait && last > 0) {
        timeout = setTimeout(later, wait - last);
      } else {
        timeout = null;
        if (!immediate) {
          result = func.apply(context, args);
          if (!timeout) context = args = null;
        }
      }
    };

    return function (...args) {
      context
      timestamp = +new Date();
      const callNow = immediate && !timeout;
      if (!timeout) timeout = setTimeout(later, wait);
      if (callNow) {
        result = func.apply(context, args);
        context = args = null as any;
      }
      return result;
    };
  };

  const resizeHandler = debounce(() => {
    if (chartRef.value) {
      chartRef.value.resize();
    }
  }, 100, true);

  const sidebarResizeHandler = (e) => {
    if (e.propertyName === 'width') {
      resizeHandler();
    }
  };

  onMounted(() => {
    window.addEventListener('resize', resizeHandler);
    sidebarElm.value = document.getElementsByClassName('sidebar-container')[0];
    sidebarElm.value && sidebarElm.value.addEventListener('transitionend', sidebarResizeHandler);
  });

  onBeforeUnmount(() => {
    window.removeEventListener('resize', resizeHandler);
    sidebarElm.value && sidebarElm.value.removeEventListener('transitionend', sidebarResizeHandler);
  });

  return {
    sidebarElm,
  };
}
