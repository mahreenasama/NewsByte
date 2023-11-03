import { createRouter, createWebHistory } from 'vue-router';

import Home from './components/Home.vue';

import Login from './components/signing/Login.vue';
import Logout from './components/signing/Logout.vue';

import EditorDashboard from './components/user/EditorDashboard.vue';

import EditorNews from './components/news/EditorNews.vue';

import Tags from './components/tag/Tags.vue';
import CreateTag from './components/tag/CreateTag.vue';
import UpdateTag from './components/tag/UpdateTag.vue';

import Newspapers from './components/newspaper/Newspapers.vue';

const routes = [
{
     path: '/',
     redirect: { path: "/home" },
   },
   {
        path: '/home',
        name: 'homePage',
        component: Home,
      },
{
     path: '/login',
     name: 'loginPage',
     component: Login,
   },
   {
        path: '/logout',
        name: 'logoutPage',
        component: Logout,
      },
            {
                    path: '/editor-dashboard',
                    name: 'editorDashboardPage',
                    component: EditorDashboard,
                  },
                  {
                                            path: '/editor-dashboard/tags',
                                            name: 'TagsPage',
                                            component: Tags,
                                          },
                  {
                          path: '/editor-dashboard/create-tag',
                          name: 'createTagPage',
                          component: CreateTag,
                        },
                  {
                                      path: '/editor-dashboard/update-tag/:id',
                                      name: 'updateTagPage',
                                      component: UpdateTag,
                                    },
                                    {
                                            path: '/editor-dashboard/channels',
                                            name: 'newspapersPage',
                                            component: Newspapers,
                                          },
{
        path: '/editor-dashboard/news/:filterType/:filterName',
        name: 'editorNewsPage',
        component: EditorNews,
      },



];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
