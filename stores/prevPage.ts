import { ref } from 'vue'

export const prevPage = function () {
	const pageName = ref({})

	const setPrevPage = (val : object) => {
		pageName.value = val
	}
	return {
		pageName,
		setPrevPage
	}
}