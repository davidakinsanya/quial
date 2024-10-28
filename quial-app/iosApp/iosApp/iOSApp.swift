import SwiftUI
import AuthenticationServices

class AppDelegate: NSObject, UIApplicationDelegate {
  func application(_ application: UIApplication,
                   didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {

     FirebaseApp.configure()
     AppInitializer.shared.initialize(isDebug: true, onKoinStart: { _ in })
     var handled: Bool

     // Let Google Sign-In handle the URL if it's related to Google Sign-In
     handled = GIDSignIn.sharedInstance.handle(url)
     return handled
  }
}

@main
struct YourApp: App {
  // register app delegate for Firebase setup
  @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
  
  var body: some Scene {
    WindowGroup {
      NavigationView {
        ContentView().onOpenURL {
            url in GIDSignIn.sharedInstance.handle(url)
        }
      }
    }
  }
}
