import { mount } from '@vue/test-utils'
import ProductForm from '../ProductForm.vue'

describe('ProductForm', () => {
  it('emite submit com dados corretos', async () => {
    const wrapper = mount(ProductForm, {
      props: { modelValue: { name: '', code: '', price: 0 } }
    })
    await wrapper.find('input').setValue('Café Premium')
    await wrapper.findAll('input')[1].setValue('PREM01')
    await wrapper.findAll('input')[2].setValue('25')
    await wrapper.find('form').trigger('submit.prevent')
    expect(wrapper.emitted().submit).toBeTruthy()
    expect(wrapper.emitted().submit[0][0]).toEqual({ name: 'Café Premium', code: 'PREM01', price: 25 })
  })
})
