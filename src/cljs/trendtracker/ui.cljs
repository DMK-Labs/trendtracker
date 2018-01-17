(ns trendtracker.ui
  (:require [trendtracker.ui.components.breadcrumbs :as breadcrumbs]
            [trendtracker.ui.components.cascader :as cascader]
            [trendtracker.ui.components.date-range :as date-range]
            [trendtracker.ui.components.footer :as footer]
            [trendtracker.ui.components.header :as header]
            [trendtracker.ui.components.scatter-chart :as scatter-chart]
            [trendtracker.ui.components.sider :as sider]
            [trendtracker.ui.components.snapshot :as snapshot]
            [trendtracker.ui.components.tabbed-charts :as tabbed-charts]
            [trendtracker.ui.pages.dashboard :as dashboard]
            [trendtracker.ui.pages.keyword-tool :as keyword-tool]
            [trendtracker.ui.pages.login :as login]
            [trendtracker.ui.pages.optimize :as optimize]
            [trendtracker.ui.pages.optimize-new :as optimize-new]
            [trendtracker.ui.pages.overview :as overview]
            [trendtracker.ui.pages.user :as user]
            [trendtracker.ui.root :as root]))

(def ui
  {
   ;; Layout
   :main root/component
   :header header/component
   :sider sider/component
   :footer footer/component
   :breadcrumbs breadcrumbs/component

   ;; Components
   :date-range-picker date-range/component
   :snapshot snapshot/component
   :tabbed-charts tabbed-charts/component
   :cascader cascader/component
   :scatter-chart scatter-chart/component

   ;; Pages
   :login-page login/component
   :user-page user/component
   :dashboard-page dashboard/component
   :keyword-tool-page keyword-tool/component
   :optimize-page optimize/component
   :optimize-new-page optimize-new/component
   :overview-page overview/component})
