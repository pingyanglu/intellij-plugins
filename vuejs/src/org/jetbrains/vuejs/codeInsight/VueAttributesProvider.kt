package org.jetbrains.vuejs.codeInsight

import com.intellij.psi.PsiElement
import com.intellij.psi.meta.PsiPresentableMetaData
import com.intellij.psi.xml.XmlTag
import com.intellij.util.ArrayUtil
import com.intellij.xml.XmlAttributeDescriptor
import com.intellij.xml.XmlAttributeDescriptorsProvider
import com.intellij.xml.impl.BasicXmlAttributeDescriptor
import icons.VuejsIcons
import javax.swing.Icon

class VueAttributesProvider : XmlAttributeDescriptorsProvider{
  companion object {
    val DEFAULT_BINDABLE = arrayOf("key", "is")
    val DEFAULT = arrayOf("v-text", "v-html", "v-show", "v-if", "v-else", "v-else-if", "v-for",
                          "v-on", "v-bind", "v-model", "v-pre", "v-cloak","v-once",
                          "slot", "ref").
                  plus(DEFAULT_BINDABLE.map { "v-bind:" + it }).
                  plus(DEFAULT_BINDABLE.map { ":" + it })

    fun vueAttributeDescriptor(attributeName: String?): VueAttributeDescriptor? {
      if (DEFAULT.contains(attributeName!!)) return VueAttributeDescriptor(attributeName)
      if (attributeName.startsWith(":") || attributeName.startsWith("v-bind:")) return VueAttributeDescriptor(attributeName)
      if (attributeName.startsWith("@") || attributeName.startsWith("v-on:")) return VueAttributeDescriptor(attributeName)
      return null
    }
  }
  override fun getAttributeDescriptors(context: XmlTag?): Array<out XmlAttributeDescriptor> {
    return DEFAULT.map(::VueAttributeDescriptor).toTypedArray()
  }

  override fun getAttributeDescriptor(attributeName: String?, context: XmlTag?): XmlAttributeDescriptor? {
    return vueAttributeDescriptor(attributeName)
  }
}

class VueAttributeDescriptor(private val name:String) : BasicXmlAttributeDescriptor(), PsiPresentableMetaData {
  override fun getName() = name
  override fun getDeclaration() = null
  override fun init(element: PsiElement?) {}
  override fun isRequired() = false
  override fun isFixed() = false
  override fun hasIdType() = false
  override fun getDependences(): Array<out Any> = ArrayUtil.EMPTY_OBJECT_ARRAY
  override fun hasIdRefType() = false
  override fun getDefaultValue() = null
  override fun isEnumerated() = false
  override fun getEnumeratedValues(): Array<out String> = ArrayUtil.EMPTY_STRING_ARRAY
  override fun getTypeName() = null
  override fun getIcon(): Icon = VuejsIcons.Vue
}