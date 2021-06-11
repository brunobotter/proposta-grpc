package br.com.bruno.validacao

import org.hibernate.validator.constraints.CompositionType
import org.hibernate.validator.constraints.ConstraintComposition
import org.hibernate.validator.constraints.br.CNPJ
import org.hibernate.validator.constraints.br.CPF
import java.lang.annotation.*
import java.lang.annotation.Retention
import java.lang.annotation.Target
import javax.validation.Constraint
import javax.validation.Payload
import javax.validation.ReportAsSingleViolation
import kotlin.reflect.KClass

@CPF
@CNPJ
@ConstraintComposition(CompositionType.OR) // specifies OR as boolean operator instead of AND
@ReportAsSingleViolation // the error reports of each individual composing constraint are ignored
@Constraint(validatedBy = []) // we don't need a validator :-)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
annotation class CpfOuCnpj(val message: String = "não é um formato válido de UUID",
                           val groups: Array<KClass<Any>> = [],
                           val payload: Array<KClass<Payload>> = [],)