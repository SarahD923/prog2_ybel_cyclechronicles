package cyclechronicles;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ShopTest {
  private Shop shop;

  @BeforeEach
  void setUp() {
    shop = new Shop();
  }

  @ParameterizedTest
  @EnumSource(
      value = Type.class,
      names = {"RACE", "SINGLE_SPEED", "FIXIE"})
  @DisplayName("TF1: Unterstützte Fahrradtypen werden angenommen")
  void acceptsSupportedBicycleTypes(Type type) {
    Order order = createOrder(type, "Alex");

    assertTrue(shop.accept(order));
  }

  @Test
  @DisplayName("TF2: Ein Gravel-Bike wird abgelehnt")
  void rejectsGravelBike() {
    Order order = createOrder(Type.GRAVEL, "Alex");

    assertFalse(shop.accept(order));
  }

  @Test
  @DisplayName("TF3: Ein E-Bike wird abgelehnt")
  void rejectsEBike() {
    Order order = createOrder(Type.EBIKE, "Alex");

    assertFalse(shop.accept(order));
  }

  @Test
  @DisplayName("TF4: Ein zweiter offener Auftrag desselben Kunden wird abgelehnt")
  void rejectsSecondOrderForSameCustomer() {
    assertTrue(shop.accept(createOrder(Type.RACE, "Alex")));

    Order secondOrder = createOrder(Type.FIXIE, "Alex");

    assertFalse(shop.accept(secondOrder));
  }

  @Test
  @DisplayName("TF5: Ein Auftrag eines anderen Kunden wird angenommen")
  void acceptsOrderForDifferentCustomer() {
    assertTrue(shop.accept(createOrder(Type.RACE, "Alex")));

    Order secondOrder = createOrder(Type.FIXIE, "Bea");

    assertTrue(shop.accept(secondOrder));
  }

  @Test
  @DisplayName("TF6: Bei vier offenen Aufträgen wird der fünfte angenommen")
  void acceptsFifthOrder() {
    addValidOrders(4);

    Order fifthOrder = createOrder(Type.SINGLE_SPEED, "Kunde 5");

    assertTrue(shop.accept(fifthOrder));
  }

  @Test
  @DisplayName("TF7: Bei fünf offenen Aufträgen wird der sechste abgelehnt")
  void rejectsSixthOrder() {
    addValidOrders(5);

    Order sixthOrder = createOrder(Type.SINGLE_SPEED, "Kunde 6");

    assertFalse(shop.accept(sixthOrder));
  }

  private void addValidOrders(int amount) {
    for (int i = 1; i <= amount; i++) {
      assertTrue(shop.accept(createOrder(Type.RACE, "Kunde " + i)));
    }
  }

  private Order createOrder(Type type, String customer) {
    Order order = mock(Order.class);
    when(order.getBicycleType()).thenReturn(type);
    when(order.getCustomer()).thenReturn(customer);
    return order;
  }
}
