//class ExampleTest {
//    @Test
//    void myFirstTest() {
//        assertEquals(1, 1);
//    }
//
//    @Test
//    @DisplayName("MultipleAssertionsTest")
//    void multipleAssertionsTest() {
//        List<Integer> numbers = List.of(2, 3, 4, 5);
//        Assertions.assertAll(
//                () -> assertEquals(2, numbers.get(0)),
//                () -> assertEquals(3, numbers.get(1)),
//                () -> assertEquals(4, numbers.get(2)),
//                () -> assertEquals(5, numbers.get(3)));
//    }
//
//
//    @Test
//    @DisplayName("Should only run the test if some criteria are met")
//    void shouldOnlyRunTheTestIfSomeCriteriaAreMet() {
//        Assumptions.assumeTrue(1 != 1);
//        // these tests only apply to a recent API version
//        assertEquals(1, 1);
//    }
//
//
//    @ParameterizedTest(name = "{0}")
//    @DisplayName("Should create shapes with different numbers of sides")
//    @ValueSource(ints = {3, 4, 5, 8, 14})
//    void shouldCreateShapesWithDifferentNumbersOfSides(int expectedNumberOfSides) {
//        Shape shape = new Shape(expectedNumberOfSides);
//        assertEquals(expectedNumberOfSides, shape.numberOfSides());
//    }
//
//
//    @ParameterizedTest
//    @DisplayName("Should not create shapes with invalid numbers of sides")
//    @ValueSource(ints = {0, 1, 2, Integer.MAX_VALUE})
//    void shouldNotCreateShapesWithInvalidNumbersOfSides(int expectedNumberOfSides) {
//        assertThrows(IllegalArgumentException.class,
//                () -> new Shape(expectedNumberOfSides));
//    }
//
//
//    @DisplayName("When a shape has been created")
//    class WhenShapeExists {
//        private final Shape shape = new Shape(4);
//
//        @Nested
//        @DisplayName("Should allow")
//        class ShouldAllow {
//            @Test
//            @DisplayName("seeing the number of sides")
//            void seeingTheNumberOfSides() {
//                assertEquals(4, shape.numberOfSides());
//            }
//
//            @Test
//            @DisplayName("seeing the description")
//            void seeingTheDescription() {
//                assertEquals("Square", shape.description());
//            }
//        }
//
//        @Nested
//        @DisplayName("Should not")
//        class ShouldNot {
//            @Test
//            @DisplayName("be equal to another shape with the same number of sides")
//            void beEqualToAnotherShapeWithTheSameNumberOfSides() {
//                assertNotEquals(new Shape(4), shape);
//            }
//        }
//    }
//}


