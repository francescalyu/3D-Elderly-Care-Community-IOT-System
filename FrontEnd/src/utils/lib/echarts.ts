import * as echarts from 'echarts/core'

import {
  BarChart,
  GaugeChart,
  LineChart,
  MapChart,
  PictorialBarChart,
  PieChart,
  RadarChart,
  GraphChart,
} from 'echarts/charts'

import {
  AriaComponent,
  CalendarComponent,
  DataZoomComponent,
  GridComponent,
  LegendComponent,
  MarkPointComponent,
  ParallelComponent,
  PolarComponent,
  RadarComponent,
  TimelineComponent,
  TitleComponent,
  ToolboxComponent,
  TooltipComponent,
  VisualMapComponent,
  GraphicComponent,
} from 'echarts/components'

import { CanvasRenderer } from 'echarts/renderers'

echarts.use([
  LegendComponent,
  TitleComponent,
  TooltipComponent,
  GridComponent,
  PolarComponent,
  AriaComponent,
  ParallelComponent,
  BarChart,
  LineChart,
  PieChart,
  MapChart,
  RadarChart,
  GraphChart,
  GaugeChart,
  CanvasRenderer,
  PictorialBarChart,
  RadarComponent,
  ToolboxComponent,
  DataZoomComponent,
  VisualMapComponent,
  TimelineComponent,
  CalendarComponent,
  MarkPointComponent,
  GraphicComponent,
])

export default echarts
