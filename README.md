5.1 Basic STS Protocol

Links: 
DSA protocol: https://github.com/zhaohuabing/digital-signature/blob/master/src/main/java/com/zhaohuabing/signature/DigitalSignature.java



The STS protocol consists of Diffie-Hellman key establishment [Diff76], followed by an
exchange of authentication signatures. In the basic version of the protocol, we assume
that the parameters used for the key establishment (i.e., the specification of a particular
cyclic group and the corresponding primitive element α) are fixed and known to all users.
While we refer to the Diffie-Hellman operation as exponentiation, implying that the
underlying group is multiplicative, the description applies equally well to additive groups
(e.g., the group of points of an elliptic curve over a finite field). We also assume in this
section that Alice knows Bob’s authentic public key, and vice versa; this assumption is
dropped in the following section.
The protocol begins with one party, Alice, creating a random number x and sending the
exponential αx to the other party, Bob (see diagram below). Bob creates a random
number y and uses Alice’s exponential to compute the exchanged key K = αxy. Bob
responds with the exponential αy and a token consisting of his signature on the
exponentials, encrypted with K using a suitable symmetric encryption algorithm E (i.e.,
EK(sB{αy, αx})). Alice computes K, decrypts the token using K, and verifies Bob’s
signature using Bob’s public key. Alice sends to Bob her corresponding encrypted
signature on the exponentials, EK(sA{αx, αy}). Finally, Bob similarly verifies Alice’s
encrypted signature using K and Alice’s public key. The security of the exponential key
exchange relies on the apparent intractability of the discrete logarithm problem [Odly91].




Sb znaci signature
Basic STS Protocol:


Alice       to                 Bob

             αx
      ------------------>
      
      αy, EK(sB{αy, αx})
      <----------------

       EK(sA{αx, αy})
      ----------------->
      
      
      
It is possible to create a more symmetric version of this protocol where the parties
exchange exponentials first and then exchange encrypted signatures in separate messages.
This would make it permissible for the exponential messages to cross, and then the
encrypted signature messages to cross. In such a case, neither Alice nor Bob need know
who initiated the call. This is desirable, as situations exist in practice (e.g. in both voice
telephony and X.25 data transfer) in which at certain implementation levels, it is not
known which party initiated a call. This explains why each party forms his signature
with his own exponential listed first. If the exponentials were in the same order in both
10
signatures, then Alice and Bob would have to find a way to agree on whose exponential
should be listed first (such as by basing the decision on which party initiated the call).
At this point, consider what assurances the STS protocol provides to the participants.
From Bob’s point of view, as a result of the Diffie-Hellman key exchange, he shares a
key known only to him and the other participant who may or may not be Alice. Our
assumption in this section is that Bob knows Alice’s public key (this is achieved in the
section below through use of certificates). Because Alice has signed the particular
exponentials associated with this run, one of which Bob himself has just created
specifically for this run, her signature is tied to this run of the protocol. By encrypting
her signature with K, Alice demonstrates to Bob that she was the party who created x.
This gives Bob assurance that the party he carried the key exchange out with was, in fact,
Alice. Alice gets a similar set of assurances from Bob.
 
