package hci.mobile.poty.data.model

class LinkPaymentData (
    val id: Int,
    val type: LinkPaymentType,
    val amount: Float,
    val balanceBefore: Float,
    val balanceAfter: Float,
    val pending: Boolean,
    val linkUuid: String?,
    val createdAt: String,
    val updatedAt: String,
    val card: Card?
) {

}