<template>
    <div class="container-fluid text-start">
        <div class="row">
            <div class="col-2">
                <EditorMenu />
            </div>
            <div class="col">
                <div id="tags-div" class="p-5 text-start">
                        <div class="row">
                            <div class="col">
                                <h2>Tags</h2>
                            </div>
                            <div class="col text-end">
                                <button class="btn btn-success" @click="this.$router.push('/editor-dashboard/create-tag')">+ Create Tag</button>
                            </div>
                            <hr />
                        </div>
                        <table v-if="tags.length > 0" class="table table-hover table-secondary text-center">
                                                    <thead>
                                                        <tr>
                                                            <th>Name</th>
                                                            <th>Description</th>
                                                            <th>Status</th>
                                                            <th>Actions</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr v-for="tagItem in tags" :key="tagItem.name">
                                                            <td>{{ tagItem.name }}</td>
                                                            <td>{{ tagItem.description }}</td>
                                                            <td>{{ tagItem.status }}</td>
                                                            <td>
                                                                <button v-if="tagItem.status === 'enabled'" class="btn btn-warning me-1" @click="this.$router.push('/editor-dashboard/update-tag/'+tagItem.name)">Update</button>
                                                                <button v-if="tagItem.status === 'enabled'" class="btn btn-danger me-1" @click="changeTagStatus(tagItem.name, tagItem, 'disable')">Disable</button>
                                                                <button v-if="tagItem.status === 'disabled'" class="btn btn-primary me-1" @click="changeTagStatus(tagItem.name, tagItem, 'enable')">Enable</button>
                                                                <button v-if="tagItem.status === 'enabled'" class="btn btn-info me-1" @click="this.$router.push('/editor-dashboard/news/tag/'+tagItem.name)">View Associated News</button>
                                                            </td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                    </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
import EditorMenu from '../user/EditorMenu.vue';

    export default {
        name: 'TagsComponent',
components: {
      EditorMenu,
    },

        data() {
                  return {
                  tags: [],
                  tag: {},
                  }
                  },
                  mounted() {
                  this.fetchTags();
                  },
                  methods: {
                  async fetchTags() {
                      try {
                          const response = await axios.get('/newsbyte/tags?filter=all');
                          this.tags = response.data.tags.slice(1);

                      } catch (error) {
                          console.error('Error fetching tags:', error);
                      }
                  },
                  async changeTagStatus(id, tag, action) {
                      try {
                          await axios.put(`/newsbyte/tags/${id}?action=${action}`, tag);
                          this.fetchTags();
                      } catch (error) {
                          console.error('Error del tag:', error);
                      }
                  },


                  }
    }

</script>