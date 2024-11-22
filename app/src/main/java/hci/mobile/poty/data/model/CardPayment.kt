package hci.mobile.poty.data.model

import hci.mobile.poty.data.network.model.NetworkCardPayment


class CardPayment (
    val amount: Double,
    val description: String,
    val type: String = "CARD",
    val cardId: Int,
    val receiverEmail: String
) {
      fun asNetworkModel(): NetworkCardPayment {
          return NetworkCardPayment(
              amount = amount,
              description = description,
              type = type,
              cardId = cardId,
              receiverEmail = receiverEmail
          )
      }
  }