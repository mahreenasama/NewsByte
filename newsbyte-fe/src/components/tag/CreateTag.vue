<template>
    <div class="container-fluid text-start">
        <div class="row">
            <div class="col-2">
                <EditorMenu />
            </div>
            <div class="col">
                <div id="create-tag-div" class="p-5">
                    <div class="" role="alert" id="tag-created-alert">
                    </div>

                    <div class="">
                                                                            <h2>Create Tag</h2>
                                                                        <hr />
                                                                    </div>
                    <form @submit.prevent="createTag">
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">Name</label>
                            <div class="col-sm-5">
                                <input class="form-control" type="text" placeholder="Name" required v-model="tag.name"
                                    autofocus />
                            </div>
                        </div>
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">Description</label>
                            <div class="col-sm-5">
                                <textarea class="form-control" rows="5" placeholder="Description"
                                    v-model="tag.description" required> </textarea>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-2"></div>
                            <div class="col-sm-5">
                                <button class="btn btn-success" type="submit">Create</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from 'axios';
import EditorMenu from '../user/EditorMenu.vue';

    export default {
        name: 'CreateTagComponent',
components: {
      EditorMenu,
    },

        data() {
            return {
                tag: {},
            }
        },
        methods: {
            async createTag() {
                try {
                    this.tag.id = 0;
                    const response = await axios.post('/newsbyte/tags', this.tag);
                    console.log('tag created successfully', response);
                    document.getElementById('tag-created-alert').setAttribute("class", "alert alert-success");
                    document.getElementById('tag-created-alert').innerHTML = 'Tag Created Successfully!';
                    this.tag = {};
                } catch (error) {
                    console.error('Error in creating tag', error);
                }
            },
        }
    }
</script>