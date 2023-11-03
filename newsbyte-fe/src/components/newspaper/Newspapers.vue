<template>
    <div class="container-fluid text-start">
        <div class="row">
            <div class="col-2">
                <EditorMenu />
            </div>
            <div class="col">
                <div id="tags-div" class="p-5 pt-0 text-start">
                        <div class="row p-5">
                            <div class="col">
                                <h2>Channels</h2>
                            </div>
                            <div class="col text-end">
                                <button class="btn btn-success">+ Create Channel</button>
                            </div>
                            <hr />
                        </div>
                        <div class="p-5">
                            <p>manage channels here</p>
                        </div>

                    </div>
            </div>
        </div>
    </div>
</template>

<script>
import EditorMenu from '../user/EditorMenu.vue';

    export default {
        name: 'NewspapersComponent',
components: {
      EditorMenu,
    },


    }

</script>