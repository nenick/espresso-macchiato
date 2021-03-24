package de.nenick.espressomacchiato.testtools

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/** Helper to create [Type] references for generic classes. */
abstract class GenericTypeResolver<out A>(
        private val lambdaGenericArgumentName: String? = null,
        private val lambdaGenericArgumentType: Type? = null
) : Type {

    override fun getTypeName(): String {
        val thisGenericTypeResolver = this::class.java.genericSuperclass as ParameterizedType
        val genericToResolve = thisGenericTypeResolver.actualTypeArguments[0] as ParameterizedType
        return genericToResolve.typeName
                .optionalFixFunctionUnitType()
                .optionalFixFunctionGenericArgumentType()
    }

    override fun toString(): String {
        return typeName
    }

    override fun equals(other: Any?): Boolean {
        if (other !is ParameterizedType) return false
        return typeName == other.typeName
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    /**
     * When a type for [kotlin.jvm.functions.Function1] is [Unit] (e.g. lambda return type)
     * the result would something with "? extends kotlin.Unit" but Constructor<T>.parameters[..].parameterizedType
     * does correctly produce "kotlin.Unit". So we adjust our result to match the more real behavior.
     */
    private fun String.optionalFixFunctionUnitType() = replace("? extends kotlin.Unit", "kotlin.Unit")

    /**
     * When a type for [kotlin.jvm.functions.Function1] is based on a generic type by the using class the result would
     * something with "? extends GENERIC_TYP_NAME" but Constructor<T>.parameters[..].parameterizedType
     * does correctly produce "? extends ExplicitClassType". So we adjust our result to match the more real behavior.
     */
    private fun String.optionalFixFunctionGenericArgumentType() = replace("? super $lambdaGenericArgumentName", "? super ${lambdaGenericArgumentType?.typeName}")

    fun isGeneric() {

    }
}