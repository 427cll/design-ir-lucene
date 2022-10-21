
const routes = [
  {
    path: "/",
    redirect: "/search"
  }, {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '/search', component: () => import('pages/SearchPage.vue') },
      { path: '/manage', component: () => import('pages/ManagePage.vue') },
    ]
  }, {
    path: '/chapter/:id',
    component: () => import('pages/ChapterPage.vue')
  }, {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  },
]

export default routes
