package scothello.view.utils

import scalafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleObjectProperty
import scalafx.beans.value.ObservableValue
import scalafx.event.subscriptions.Subscription

class ResettableObjectProperty[T](initialValue: T) extends ObjectProperty[T](new SimpleObjectProperty[T](initialValue)):

  private var subscriptions: List[Subscription] = List.empty
  private var derivedProperties: List[ResettableObjectProperty[_]] = List.empty

  override def onChange[J1 >: T](op: (ObservableValue[T, T], J1, J1) => Unit): Subscription =
    val subscription = super.onChange(op)
    subscriptions = subscriptions :+ subscription
    subscription

  def map[R](f: T => R): ResettableObjectProperty[R] =
    val mappedProperty = new ResettableObjectProperty(f(value))

    val subscription = onChange { (_, _, newValue) =>
      mappedProperty() = f(newValue)
    }
    subscriptions = subscriptions :+ subscription
    derivedProperties = derivedProperties :+ mappedProperty

    mappedProperty

  def removeListeners(): Unit =
    subscriptions.foreach { subscription =>
      subscription.cancel()
    }
    derivedProperties.foreach(_.removeListeners())
    subscriptions = List.empty
