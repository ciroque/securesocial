package securesocial.core.providers

import play.api.Application
import securesocial.core._
import play.api.mvc.{Result, Request}
import securesocial.core.IdentityId


class LdapProvider(application: Application) extends IdentityProvider(application) {
  override def id: String = LdapProvider.LDAP

  val settings = createSettings()

  private def createSettings(): LdapSettings = {
    val result = for {
      servers <- loadProperty(LdapSettings.Servers)
      userSearchBase <- loadProperty(LdapSettings.UserSearchBase)
      userSearchFilter <- loadProperty(LdapSettings.UserSearchFilter)
      groupSearchBase <- loadProperty(LdapSettings.GroupSearchBase)
      groupSearchFilter <- loadProperty(LdapSettings.GroupSearchFilter)
      groupMembershipFilter <- loadProperty(LdapSettings.GroupMembershipFilter)
      managerDN <- loadProperty(LdapSettings.ManagerDN)
      managerPassword <- loadProperty(LdapSettings.ManagerPassword)
      displayNameAttribute <- loadProperty(LdapSettings.DisplayNameAttribute)
      emailAddressAttribute <- loadProperty(LdapSettings.EmailAddressAttribute)

    } yield {
      LdapSettings(
        servers,
        userSearchBase,
        userSearchFilter,
        groupSearchBase,
        groupSearchFilter,
      groupMembershipFilter,
      managerDN,
      managerPassword,
      displayNameAttribute,
      emailAddressAttribute)
    }
    if (!result.isDefined) {
      throwMissingPropertiesException()
    }
    result.get
  }


  /**
   * Subclasses need to implement this method to populate the User object with profile
   * information from the service provider.
   *
   * @param user The user object to be populated
   * @return A copy of the user object with the new values set
   */
  override def fillProfile(user: SocialUser): SocialUser = {
    user
  }

  /**
   * Subclasses need to implement the authentication logic. This method needs to return
   * a User object that then gets passed to the fillProfile method
   *
   * @param request
   * @tparam A
   * @return Either a Result or a User
   */
  override def doAuth[A]()(implicit request: Request[A]): Either[Result, SocialUser] = {


    Right(SocialUser(IdentityId("uid", "prid"), "firstName", "lastName", "fullName", None, None, AuthenticationMethod.Ldap, None, None, None))
  }

  /**
   * Subclasses need to implement this to specify the authentication method
   * @return
   */
  override def authMethod: AuthenticationMethod = AuthenticationMethod.Ldap
}

object LdapProvider {
  val LDAP = "ldap"
}

case class LdapSettings(servers: String,
                        userSearchBase: String,
                        userSearchFilter: String,
                        groupSearchBase: String,
                        groupSearchFilter: String,
                        groupMembershipFilter: String,
                        managerDN: String,
                        managerPassword: String,
                        displayNameAttribute: String,
                        emailAddressAttribute: String)

object LdapSettings {
  val Servers = "servers"
  val UserSearchBase = "userSearchBase"
  val UserSearchFilter = "userSearchFilter"
  val GroupSearchBase = "groupSearchBase"
  val GroupSearchFilter = "groupSearchFilter"
  val GroupMembershipFilter = "groupMembershipFilter"
  val ManagerDN = "managerDN"
  val ManagerPassword = "managerPassword"
  val DisplayNameAttribute = "displayNameAttribute"
  val EmailAddressAttribute = "emailAddressAttribute"
}
