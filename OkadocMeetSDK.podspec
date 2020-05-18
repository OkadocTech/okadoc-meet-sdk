Pod::Spec.new do |s|
  s.name             = 'OkadocMeetSDK'
  s.version          = '0.1.0'
  s.summary          = 'Okadoc Meet iOS SDK'
  s.description      = 'Okadoc Meet iOS SDK for Telemedicine.'
  s.homepage         = 'https://github.com/OkadocTech/okadoc-meet-sdk'
  s.license          = 'Apache 2'
  s.authors          = 'Okadoc Tech'
  s.source           = { :git => 'https://github.com/OkadocTech/okadoc-meet-sdk.git', :tag => s.version }

  s.platform         = :ios, '10.0'

  s.vendored_frameworks = 'Frameworks/ios/OkadocMeet.framework', 'Frameworks/ios/WebRTC.framework'
end
