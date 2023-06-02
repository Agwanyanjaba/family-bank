# family-bank application
<h3>How to get started.

<h3>Sample cURL request</h3>

<h3>Sample logs [Bank microservice]</h3>

2023-06-02T12:44:43.834+03:00  INFO 48689 --- [nio-9090-exec-1] c.f.controllers.PaymentController        : Student validation response is complete
2023-06-02T12:44:43.834+03:00  INFO 48689 --- [nio-9090-exec-1] c.f.controllers.PaymentController        : ===Payment Request PaymentRequest{paymentId='9a1a9e2c-ffc3-11ed-be56-0242ac1200011', studentId=1, amount=1000.50, paymentDescription='Test Fee Payment', paymentMethod='Card Payment', paymentChannel='online banking', paymentDate=null}
2023-06-02T12:44:43.941+03:00  INFO 48689 --- [nio-9090-exec-1] c.f.s.FeePaymentNotificationService      : === RESPONSE RECEIVED[ {paymentId=9a1a9e2c-ffc3-11ed-be56-0242ac1200011, studentId=1, studentName=John Doe, amountPaid=1000.5, paymentDescription=Test Fee Payment}

<h3>Sample logs [Pesapap microservice]</h3>
2023-06-02T12:37:05.154+03:00  INFO 40617 --- [nio-9091-exec-7] c.p.services.StudentService              : ===VALIDATION REQUEST for :: 1
2023-06-02T12:37:05.154+03:00  INFO 40617 --- [nio-9091-exec-7] c.p.c.StudentValidationController        : === [Validation Response sent
2023-06-02T12:37:05.202+03:00  INFO 40617 --- [nio-9091-exec-3] c.p.services.StudentService              : ===[FEE_PAYMENT Notification Received :: FeePayment Details{paymentId=9a1a9e2c-ffc3-11ed-be56-0242ac1200011, studentId=1, amount=1000.50, paymentChannel='online banking', paymentDescription='Test Fee Payment', paymentDate=null}
